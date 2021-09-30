#Download Node Alpine image
FROM node:14-alpine As build

#Setup the working directory
WORKDIR ./app

EXPOSE 4200

#Copy package.json
COPY package.json package-lock.json ./

#Install dependencies
RUN npm install

#Copy other files and folder to working directory
COPY . .

RUN npm run build

#Download NGINX Image
FROM nginx:1.15.8-alpine

#Copy built angular app files to NGINX HTML folder
COPY --from=build ./app/dist /usr/share/nginx/html
