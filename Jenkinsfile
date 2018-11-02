pipeline {
  agent {
    label 'maven'
  }
  stages {
    stage('Build') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('bc', 'ROOT').exists();
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp('redhat-openjdk18-openshift:1.1~https://github.com/ringo69/Spring-Boot')
          }
        }
      }
    }
  }
}