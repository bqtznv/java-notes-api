# Service Contract

The service follows the INF 345 M1 contract:

- `scripts/run.sh` starts the service and respects the `PORT` environment variable.
- The service binds to `0.0.0.0` so it also works inside a container.
- `GET /healthz` returns HTTP `200` with a non-empty response.
- `scripts/test.sh` runs three independent automated tests and prints `TESTS: n/n`.
