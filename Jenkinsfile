#!groovy
@Library("platform.infrastructure.jenkinslib")
@Library('platform.infrastructure.pipelinelibrary') _
import com.ebsco.platform.Shared

static final String AWS_REGION = "us-east-1"
static final String DEVQA_ENV = "devqa"
static final String INTEGRATION_ENV = "integration"
static final String LIVE_ENV = "live"


def buildNodeId = platformDefaults.getBuildNodeLabel()

withNodeWrapper(buildNodeId) {

  // Create a groovy library object from the Shared.groovy file.
  def shared = new com.ebsco.platform.Shared()

  // Ensure we start with an empty directory.
  step([$class: 'WsCleanup'])

  // Checkout the repo from github
  stage ('checkout') {
    checkout scm
  }

  try {
  /*
   * Run gradle build tasks -- gradle assemble, test, build
   * Please ensure that gradle build invokes all of your gradle tasks
   * Stages: Compile, Test, Build
   */
  gradleBuild()
  } finally {
    // finally: make sure test reports are also published for build failures (when they areneeded most)

    /* https://jenkins.io/blog/2016/07/01/html-publisher-plugin/ */
    publishHTML(target: [
            allowMissing         : true,
            alwaysLinkToLastBuild: false,
            keepAll              : true,
            reportDir            : 'build/reports/jacoco/test/html',
            reportFiles          : 'index.html',
            reportName           : "Jacoco Report"
    ])

    /* https://jenkins.io/blog/2016/07/01/html-publisher-plugin/ */
    publishHTML(target: [
            allowMissing         : true,
            alwaysLinkToLastBuild: false,
            keepAll              : true,
            reportDir            : 'build/reports/tests/test',
            reportFiles          : 'index.html',
            reportName           : "Unit Tests Report"
    ])
   }

  /*
   * Run the SonarQube scan task  to invoke the sonar plugin
   * SonarQube only engaged for master branch
   */
  if (env.BRANCH_NAME == "master") {
      stage('Analyzing Source Code - SonarQube') {
          sh '/opt/gradle/bin/gradle sonarqube'
      }
  }

  /*
   * Builds the docker file, updates tags, publishes the new container to ecr
   * Stages: Build/Publish Docker
   */
  dockerBuildPush()

 /*
  *
  * Deploy to ECS
  * Bare bones ECS deployment
  * Stages: Deploy to ECS
  */

  def ecsDeployParametersPath = 'cloudformation/deploy/parameters.json'
  def deployNodeId = platformDefaults.getDeployNodeLabel()
  
  /*===============DevQA==============*/

  withDeployWrapper(DEVQA_ENV) 
  {
    ecsDeploy deployEnv: DEVQA_ENV, parametersPath: ecsDeployParametersPath
    //runE2ETests(DEVQA_ENV)
    performRelease releaseMethod: 'fast', deployEnv: DEVQA_ENV
  }


  /*==============Integration===========*/
  if(env.BRANCH_NAME != "master") {
     println "No Integration deployment on a branch which is not master"
     return 0;
   }
  
  //delete project workspace
  step([$class: 'WsCleanup']) 
  

  properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]])
}
