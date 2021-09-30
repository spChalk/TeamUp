#Download Node Alpine image
FROM node:14-alpine As build

#Setup the working directory
WORKDIR /app

ENV PATH /app/node_modules/.bin$PATH

COPY package*.json ./

RUN npm isntall

RUN npm install -g @angular/cli

COPY . /app

CMD ng serve --host 0.0.0.0