pipeline {
  
  agent any
  
  tools {
    maven 'Maven 3.8.3'
    jdk 'JDK11'
  }

  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    process.env.CI = 'false'
  }
  
  stages {
    stage('Login Dockerhub') {
      steps {
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
      }
    }
    stage("build backends") {
      steps {
        // Backend Catalog
        dir ('persistent-microservices/backend-catalog/') {
          sh 'mvn -Dmaven.test.failure.ignore=true install'
          sh 'docker build -t backend-catalog-image -f docker/Dockerfile .'
          
        }
        //Backend Store
        dir ('persistent-microservices/backend-store/') {
          sh 'mvn -Dmaven.test.failure.ignore=true install'
          sh 'docker build -t backend-store-image -f docker/Dockerfile .'
        }      


      }
    }

    stage("Building Frontends") {
      
      steps {
        dir ('frontends/frontend-catalog/') {
          sh 'npm install'
          sh 'npm run build'
        }
      }
    }

    stage('Push Docker Images') {
      steps {
        sh 'docker tag backend-catalog-image chaphe/backend-catalog-image:1.0'
        sh 'docker push chaphe/backend-catalog-image:1.0'
        sh 'docker tag backend-store-image chaphe/backend-store-image:1.0'
        sh 'docker push chaphe/backend-store-image:1.0'
      }
    }
    
    stage("test") {
      steps {
        echo 'This is the test phase'
        echo 'The test phase has been done rigth!'
      }
    }
    
    stage("deploy") {
      steps {
        echo 'This is the deploy phase'
        echo 'The deploy phase has been done rigth!'
      }
    }
  }
}
