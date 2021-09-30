#!/bin/bash
docker build -t front .
docker run -it -p 4200:4200 front