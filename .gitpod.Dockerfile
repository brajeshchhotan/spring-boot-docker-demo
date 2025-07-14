FROM gitpod/workspace-java-17

# This ensures Java 17 is the default in the environment
USER gitpod

# Set Java 17 as default
RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 17.0.15-tem && \
    sdk default java 17.0.15-tem"

# Set environment variables
ENV JAVA_HOME=/home/gitpod/.sdkman/candidates/java/17.0.15-tem
ENV PATH=$JAVA_HOME/bin:$PATH
