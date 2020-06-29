# Spring Boot demo

## Setup

Run `make docker` to build application Docker image, then run `make deploy` to run the app in a Docker container.
`make stop` stops the running container, and `make redeploy` restarts the deployment.

Run `make local` to start the app locally, outside a Docker container, in a development environment.

## Endpoints

Endpoints are defined in OpenAPI specification format.

The documentation is available after the application starts under `GET /v3/api-docs` endpoint in JSON format, `GET /v3/api-docs.yaml` in YAML format or as a web page under `GET /swagger-ui.html`.

## Tests

Run `make tests` to execute integration tests.
