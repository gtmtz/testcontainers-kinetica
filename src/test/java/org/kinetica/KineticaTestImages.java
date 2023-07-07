package org.kinetica;

import org.testcontainers.utility.DockerImageName;

public interface KineticaTestImages {

    DockerImageName KINETICA_INTEL_LATEST_IMAGE = DockerImageName.parse("kinetica/kinetica-intel:latest");
    DockerImageName KINETICA_CPU_LATEST_IMAGE = DockerImageName.parse("kinetica/kinetica-cpu:latest");
}
