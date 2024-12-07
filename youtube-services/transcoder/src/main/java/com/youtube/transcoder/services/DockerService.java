package com.youtube.transcoder.services;

import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DockerService {
    private DockerClientConfig standard;
    private DockerClient dockerClient;

    public DockerService() {
        this.standard = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        this.dockerClient = DockerClientBuilder.getInstance().build();
    }

    public String createAndStartContainer(String imageName, List<String> envList, String hostPath, String containerPath) {
        Volume containerVolume = new Volume(containerPath);
        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
            .withEnv(envList)
            .withHostConfig(
                HostConfig.newHostConfig()
                    .withBinds(new Bind(hostPath, containerVolume))
            )
            .withVolumes(containerVolume)
            .exec();
        dockerClient.startContainerCmd(container.getId()).exec();
        return container.getId();
    }

    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }
    
    public List<Container> listRunningContainers() {
        return dockerClient.listContainersCmd().exec();
    }
}
