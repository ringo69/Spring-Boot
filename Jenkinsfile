pipeline {
  agent {
      label 'maven'
  }
  stages {
    stage('Build App') {
      steps {
        sh "mvn install"
      }
    }
    stage('Create Image Builder') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector("bc", "restapp").exists();
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newBuild("--name=restapp", "--image-stream=redhat-openjdk18-openshift:1.1", "--binary")
          }
        }
      }
    }
    stage('Build Image') {
      steps {
        script {
          openshift.withCluster() {
            openshift.selector("bc", "restapp").startBuild("--from-file=target/gs-spring-boot.jar", "--wait")
          }
        }
      }
    }
    stage('Promote to DEV') {
      steps {
        script {
          openshift.withCluster() {
            openshift.tag("restapp:latest", "restapp:dev")
          }
        }
      }
    }
    stage('Create DEV') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('dc', 'restapp-dev').exists()
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp("restapp:latest", "--name=restapp-dev").narrow('svc').expose()
          }
        }
      }
    }
    stage('Promote TEST') {
      steps {
        script {
          openshift.withCluster() {
            openshift.tag("restapp:dev", "restapp:test")
          }
        }
      }
    }
    stage('Create TEST') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('dc', 'restapp-test').exists()
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp("restapp:test", "--name=restapp-test").narrow('svc').expose()
          }
        }
      }
    }
  }
}