resource "aws_s3_bucket" "main" {
  bucket = "${var.app_name}-${data.aws_caller_identity.current.account_id}-codebuild"
  acl = "private"

  versioning {
    enabled = true
  }
}

data "aws_iam_policy_document" "main" {
  statement {
    actions = [
      "sts:AssumeRole"
    ]

    principals {
      type = "Service"
      identifiers = [
        "codebuild.amazonaws.com",
        "codepipeline.amazonaws.com"
      ]
    }

    effect = "Allow"
  }
}

resource "aws_iam_role" "main" {
  name = "${var.app_name}-codebuild-role"
  assume_role_policy = data.aws_iam_policy_document.main.json
}

resource "aws_iam_role_policy" "main" {
  name = "${var.app_name}-codebuild-policy"
  role = aws_iam_role.main.name

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "CodeBuildDefaultPolicy",
      "Effect": "Allow",
      "Action": [
        "codebuild:*",
        "iam:PassRole"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Resource": [
        "*"
      ],
      "Action": [
        "logs:FilterLogEvents",
        "logs:GetLogEvents",
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "codebuild:CreateReportGroup",
        "codebuild:CreateReport",
        "codebuild:UpdateReportGroup",
        "codebuild:UpdateReport",
        "codebuild:BatchPutTestCases"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "ec2:DescribeDhcpOptions",
        "ec2:DescribeNetworkInterfaces",
        "ec2:DeleteNetworkInterface",
        "ec2:DescribeSubnets",
        "ec2:DescribeSecurityGroups",
        "ec2:DescribeVpcs",
        "ec2:DescribeNetworkInterfaces",
        "ec2:DescribeNetworkInterfaceAttribute",
        "ec2:DescribeNetworkInterfacePermissions",
        "ec2:CreateNetworkInterface",
        "ec2:CreateNetworkInterfacePermission",
        "ssm:GetParameters"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:*"
      ],
      "Resource": [
        "arn:aws:s3:::codepipeline-${data.aws_caller_identity.current.account_id}-${var.app_name}/*",
        "${aws_s3_bucket.main.arn}",
        "${aws_s3_bucket.main.arn}/*"
      ]
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "ecr-poweruser" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryPowerUser"
  role = aws_iam_role.main.name
}

resource "aws_codebuild_project" "main" {
  name = var.app_name
  description = "${var.app_name}_codebuild_project"
  build_timeout = var.build_timeout
  service_role = aws_iam_role.main.arn
  badge_enabled = true

  artifacts {
    type = var.artifacts
    location = var.artifacts == "S3" ? aws_s3_bucket.main.bucket : ""
    namespace_type = var.artifacts == "S3" ? var.namespace_type : null
    packaging = var.artifacts == "S3" ? var.packaging : null
  }

  cache {
    type = "S3"
    location = "${aws_s3_bucket.main.bucket}/cache"
  }

  environment {
    compute_type = var.compute_type
    image = var.image
    type = "LINUX_CONTAINER"
    image_pull_credentials_type = "CODEBUILD"
    privileged_mode = true

    environment_variable {
      name = "ARTIFACT_BUCKET"
      value = aws_s3_bucket.main.bucket
      type = "PLAINTEXT"
    }

    environment_variable {
      name = "AWS_DEFAULT_REGION"
      value = data.aws_region.current.name
      type = "PLAINTEXT"
    }

    environment_variable {
      name = "AWS_ACCOUNT_ID"
      value = data.aws_caller_identity.current.account_id
      type = "PLAINTEXT"
    }

    environment_variable {
      name = "IMAGE_REPO_NAME"
      value = var.app_name
      type = "PLAINTEXT"
    }
  }

  logs_config {
    cloudwatch_logs {
      group_name = "log-group"
      stream_name = "log-stream"
    }

    s3_logs {
      status = "ENABLED"
      location = "${aws_s3_bucket.main.id}/build-log"
    }
  }

  source {
    type = "GITHUB"
    auth {
      type = "OAUTH"
    }
    location = "https://github.com/${var.source_location}.git"
    git_clone_depth = var.git_clone_depth
  }

  vpc_config {
    vpc_id = var.vpc_id
    subnets = var.subnets
    security_group_ids = var.security_group_ids
  }

  tags = var.tags
  depends_on = [
    aws_codebuild_source_credential.main]
}

resource "aws_codebuild_webhook" "main" {
  count = var.enable_webhook == true ? 1 : 0
  project_name = aws_codebuild_project.main.name

  filter_group {
    filter {
      type = "EVENT"
      pattern = "PULL_REQUEST_CREATED, PULL_REQUEST_UPDATED, PULL_REQUEST_REOPENED"
    }
  }

  filter_group {
    filter {
      type = "EVENT"
      pattern = "PUSH"
    }

    filter {
      type = "HEAD_REF"
      pattern = var.source_branch
    }
  }
}

output "aws_codebuild_webhook" {
  value = aws_codebuild_webhook.main.*.url
}

resource "aws_codebuild_source_credential" "main" {
  auth_type = "PERSONAL_ACCESS_TOKEN"
  server_type = "GITHUB"
  token = var.github_personal_access_token
}
