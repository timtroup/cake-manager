output "badge_url" {
  value = aws_codebuild_project.main.badge_url
}

output "bucket_regional_domain_name" {
  value = aws_s3_bucket.main.bucket_regional_domain_name
}

output "bucket" {
  value = aws_s3_bucket.main.bucket
}

output "app_name" {
  value = var.app_name
}

output "role_arn" {
  value = aws_iam_role.main.arn
}

output "role_name" {
  value = aws_iam_role.main.name
}

output "project_arn" {
  value = aws_codebuild_project.main.arn
}
