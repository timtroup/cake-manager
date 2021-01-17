variable "repository" {
  type = string
  description = "The name of the github repository"
}

variable "url" {
  type = string
  description = "The url to send the event to"
}

variable "events" {
  type = list(string)
  default = [
    "push"]
}

resource "github_repository_webhook" "main" {
  repository = var.repository
  events = var.events

  configuration {
    url = var.url
    insecure_ssl = false
    content_type = "json"
  }
}
