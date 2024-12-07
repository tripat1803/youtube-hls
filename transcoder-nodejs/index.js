import express from "express";
import ffmpeg from "fluent-ffmpeg";
import ffmpegPath from "ffmpeg-static";
import path from "path";
import { fileURLToPath } from 'url';
import fs from "fs";
import cors from "cors";

const app = express();

app.use(cors({
    origin: "*"
}));

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.get('/files/:file', async (req, res) => {
    try {
        const filePath = path.join(__dirname, 'test-videos', req.params.file);

        if (!fs.existsSync(filePath)) {
            return res.status(404).send('File not found');
        }

        const readStream = fs.createReadStream(filePath);

        readStream.on('error', (err) => {
            console.error(err);
            res.status(500).send('Error reading the file');
        });

        const header = {
            'Content-Type': 'video/mp2t'
        }

        res.writeHead(200, header);
        readStream.pipe(res);
    } catch (err) {
        res.sendStatus(500);
    }
});

app.get("/transcode", async (req, res) => {
    const inputFilePath = path.join(__dirname, "assets", "animal.mp4");
    const outputPath = path.join(__dirname, "output", "master.m3u8");
    new Promise((resolve, reject) => {
        ffmpeg(inputFilePath)
            .setFfmpegPath(ffmpegPath)
            .outputOptions([
                '-c:v h264',
                // '-b:v <VIDEO BITRATE>',
                '-c:a aac',
                // '-b:a <AUDIO BITRATE>',
                // '-vf scale=<RESOLUTION>',
                '-start_number 0',
                '-g 20',
                '-keyint_min 20',
                '-hls_time 2',
                '-hls_list_size 0',
                '-f hls'
                // '-hls_segment_filename <FILE-NAME>'
            ])
            .output(outputPath)
            .on("end", () => resolve("Transcoding done"))
            .on("error", (err) => reject(err))
            .run()
    });
    res.status(200).json({
        message: "Transcoding started"
    });
});

app.get("/", async (req, res) => {
    return res.status(200).json({
        message: "Hello World"
    });
})

const PORT = 4000;
app.listen(PORT, () => {
    console.log("Server running at port", PORT);
});