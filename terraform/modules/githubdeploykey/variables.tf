variable "name" {
  description = "The title of the deploy key"
  type = string
  default = "codebuild"
}

variable "github_org_repo" {
  type = string
  default = "foo/bar"
}

variable "read_only" {
  description = "Pull only access if true, push access if false"
  default = true
}
