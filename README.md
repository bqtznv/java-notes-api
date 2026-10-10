# Java Notes API

A small HTTP service written in Java for the INF 345 M1 project.

## Requirements

- JDK 11 or newer

## Run

```bash
./scripts/run.sh
```

The service uses the `PORT` environment variable. It defaults to port `8080`.

For example:

```bash
PORT=4567 ./scripts/run.sh
```

## Test

```bash
./scripts/test.sh
```

## Endpoints

- `GET /` returns service information
- `GET /healthz` returns `200` and `ok`
- `GET /notes` returns the number of notes

## What it does

Java Notes API is a small HTTP service. `GET /` returns service information, `GET /healthz` returns `200` and `ok`, and `GET /notes` returns the number of notes.
