Tests for JAX-B API involves several different jdk/ServiceLoader configurations, so the easiest way is using bash.

Steps to run tests:

1) build jaxb-api:
  cd ../..
  mvn clean package
  cp target/ 

2) setup JDK properly (jdk8 or jdk9)
  export JAVA_HOME=~/java/jdk9-dev-clean
  export PATH=$JAVA_HOME/bin:$PATH

3) ensure endorsed
  ls -al $JAVA_HOME/jre/lib/endorsed
  mkdir -p $JAVA_HOME/jre/lib/endorsed
  cp target/jaxb-api-2.2.13-SNAPSHOT.jar $JAVA_HOME/jre/lib/endorsed/

4) ren tests:
  cd src/tests-bash/