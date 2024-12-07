#!/bin/bash

curl -o input_video.mp4 "$1"

if [[ ! -f input_video.mp4 ]]; then
    echo "Url passed $1 -> Error: input_video.mp4 not found. Exiting."
    exit 1
fi

# Process for 360p
ffmpeg -i input_video.mp4 \
  -vf scale=-2:360 -c:v h264 -b:v 800k -c:a aac -b:a 96k -preset fast -crf 23 -start_number 0 -g 20 -keyint_min 20 -hls_list_size 0 -hls_time 10 -f hls -hls_segment_filename /home/app/tmp/output_360p_%03d.ts /home/app/tmp/output_360p.m3u8

# Process for 480p
ffmpeg -i input_video.mp4 \
  -vf scale=-2:480 -c:v h264 -b:v 1500k -c:a aac -b:a 128k -preset fast -crf 23 -start_number 0 -g 20 -keyint_min 20 -hls_list_size 0 -hls_time 10 -f hls -hls_segment_filename /home/app/tmp/output_480p_%03d.ts /home/app/tmp/output_480p.m3u8

# Process for 720p
ffmpeg -i input_video.mp4 \
  -vf scale=-2:720 -c:v h264 -b:v 2500k -c:a aac -b:a 128k -preset fast -crf 23 -start_number 0 -g 20 -keyint_min 20 -hls_list_size 0 -hls_time 10 -f hls -hls_segment_filename /home/app/tmp/output_720p_%03d.ts /home/app/tmp/output_720p.m3u8

# Process for 1080p
ffmpeg -i input_video.mp4 \
  -vf scale=-2:1080 -c:v h264 -b:v 5000k -c:a aac -b:a 192k -preset fast -crf 23 -start_number 0 -g 20 -keyint_min 20 -hls_list_size 0 -hls_time 10 -f hls -hls_segment_filename /home/app/tmp/output_1080p_%03d.ts /home/app/tmp/output_1080p.m3u8

{
    echo "#EXTM3U"
    echo "#EXT-X-VERSION:3"
    echo "#EXT-X-STREAM-INF:BANDWIDTH=800000,RESOLUTION=640x360"
    echo "output_360p.m3u8"
    echo "#EXT-X-STREAM-INF:BANDWIDTH=1500000,RESOLUTION=854x480"
    echo "output_480p.m3u8"
    echo "#EXT-X-STREAM-INF:BANDWIDTH=2500000,RESOLUTION=1280x720"
    echo "output_720p.m3u8"
    echo "#EXT-X-STREAM-INF:BANDWIDTH=5000000,RESOLUTION=1920x1080"
    echo "output_1080p.m3u8"
} > /home/app/tmp/master.m3u8

echo "HLS segments and master.m3u8 created successfully."