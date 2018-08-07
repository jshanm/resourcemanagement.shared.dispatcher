# RunBook: Zuul router for Dynamic Health

## Overview
The '```resourcemanagement.shared.refurlauthmiddle```' is a Middle Tier Service to perform RefURL authentication and direct authentication requests.

### Service name:
    resourcemanagement.shared.refurlauthmiddle

## Details:

### Service Owner:

   Contact [```Dustin MacIver```](DustinMacIver@EBSCO.COM) for any questions.

### GitHub Repo:

   GitHub repository for this service is located [```here```](https://github.com/EBSCOIS/resourcemanagement.shared.refurlauthmiddle)

### How to Build and Deploy Service

   Whenever we merge changes to master in GitHub the builds will be deployed to Dev, Integration, and Live environments by Jenkins aws pipeline.

   Link to Jenkins:
   [```Jenkins Pipeline```](http://jenkins.eis-platformlive.cloud/job/resourcemanagement.shared/job/resourcemanagement.shared.refurlauthmiddle/job/master/)

### Environment

   This service runs in the 3 standard cloud environments

      DEVQA: http://eureka.vpca.us-east-1.eis-deliverydevqa.cloud:8761/

      INTEGRATION: http://eureka.vpca.us-east-1.eis-deliveryintegration.cloud:8761/

      LIVE: http://eureka.vpca.us-east-1.eis-deliverylive.cloud:8761/

### Monitoring

**Logging**

We log different types of info based on api we call. Sumologic dashboard for this service is located [```here```](https://service.us2.sumologic.com/ui/bento.html#/workspace/default/dashboard/view/QILOl42OrCEMB37C8n6UY2IkTkvJIc83iciZ9Rlxh5VDAmTmy1Y57yzBaMF2).

**Metrics**

We log different metrics like count, gauge, meter for different calls to api.

    Link to metrics using {IP address}:{port}/metrics, e.g. http://10.25.226.55:33369/metrics. Get IP address for desired environment from Eureka (see above for links)

**Health**

    Link to Health using {IP address}:{port}/health, e.g. http://10.25.226.55:33369/health

### Alerts and Escalation

#### DataDog Dashboards
Dashboard for ECS status is located [```here```](https://app.datadoghq.com/screen/225089/refurl-ecs-live?tpl_var_ecs_task=resourcemanagement_shared_refurlauthmiddle-family).

#### DataDog Monitors
CPU and Memory utilization monitors are located [```here```]( https://app.datadoghq.com/monitors/manage?q=Medical%20Gateway).

#### Sumologic Dashboards
Dashboard for 4xx error counts can be monitored [```here```](https://service.us2.sumologic.com/ui/bento.html#/workspace/default/dashboard/view/8U0USERPx50DH80oMvqyeglkowfwuI4oFJ1FoWurAZ7rtRi4i6INOyenoxyH).

#### Hystrix - TBD

### Care and Feeding

**Testing**

*Unit and Integration Tests:*

 These tests are included as a part of gradle build and could be verified based on Jenkins Build Success/failure.

*E2E Tests:*

We use Mocha and Super test to run E2E tests.

End to end tests are located inside of './src/e2e/'. This test will run agains a the live api server using command npm test

**Restart Instance:**

Allows users to restart µservice instances. This reaction can be used as a last resort when all other reactions fail to resolve the issue.

1. Sign on to Amazon AWS selecting the correct role & environment (devqa, integration, or live).
2. Open EC2 Container Service page and select ```shared-resourcemanagement``` cluster.
3. In "Clusters" page, select the "Tasks" tab and locate ```resourcemanagement_shared_refurlauthmiddle-family-service``` in the "Task Definition" column (you may want to expand the column and do a browser search on "announce")
4. To restart, you can open the task definition by clicking on the task definition name and selecting Restart (or Run Task) from the "Actions" drop-down

**Stop Instance:**

Allows users to stop µservice instances. This reaction is a bit of an edge case but could be used to stop instances that have a limited use.

1. Follow steps 1 - 3 in "Restart" above
2. To stop, you can
  a. select the checkbox at left and click 'Stop button' at the top of the list or
  b. open the task by clicking the task identifier in the left-hand column (header "Task"), then clicking the "Stop" button at the upper right on the task page

**Start Instance:**

Allows users to start µservice instances. This reaction is a bit of an edge case but could be used to start an instance that is designed to take over from another false instance.

1. Follow steps 1 - 3 in "Restart" above
2. To Start you can open the task definition by clicking on the task definition name and selecting Start (or Run Task) from the "Actions" drop-down

**Problem Cases and Reaction**

TBD
## Emergency Revert to Previous Working Build
Follow the Revert/Rollback process described at https://teams.microsoft.com/_#/docx/viewer/teams/https:~2F~2Febscoind.sharepoint.com~2Fsites~2FAuthenticators~2FShared%20Documents~2FTeam%20Documents~2FRollback%20Document%20072418.docx?threadId=19:e04e6b526dcc4a58974612db226f32ed@thread.skype&baseUrl=https:~2F~2Febscoind.sharepoint.com~2Fsites~2FAuthenticators&fileId=601599D6-508B-43DD-ABFB-AED3A829FC28&ctx=files&viewerAction=view

## Known Solutions

### Lots of Traffic/Service is Slow
Solution here is to up the number of instances of ```RefURL Auth Service``` we have running. We can do this through AWS.

  1. Sign in to AWS through SSO.

  2. Go [```here```](https://console.aws.amazon.com/ecs/home?region=us-east-1#/clusters/shared-resourcemanagement/services/resourcemanagement_shared_refurlauthmiddle-family-service/tasks).

  3. Hit the blue 'Update' button.

  4. The 'Number of tasks' is the number of instances of  ```RefURL Auth Service``` we want to be running simultaneously. Increment this number and hit 'Next step'.
  ![update-ec2-instance-count](https://user-images.githubusercontent.com/26384688/29935633-68e98e96-8e4d-11e7-9398-47f3453a7a81.png)

  5. Continue hitting 'Next step' as you progress through the 'Update Service' steps.

  6. Finally, hit 'Update Service'.

  7. A new instance of  ```RefURL Auth Service``` should be starting up to handle some of the load.

### Documentation Links:

   Runbook template (confluence): http://confluence.corp.epnet.com/display/TCO/Copy+of+Runbook+Template

   Runbook template (github): https://github.com/SkeltonThatcher/run-book-template/blob/master/run-book-template.md
