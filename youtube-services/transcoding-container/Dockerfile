FROM ubuntu:focal

RUN /usr/bin/apt-get update && \
    /usr/bin/apt-get install -y curl ffmpeg

WORKDIR /home/app
COPY ../../script.sh .
RUN chmod +x script.sh
RUN mkdir tmp
RUN chmod 777 tmp

ENV video_file_url=""

CMD ./script.sh "$video_file_url"