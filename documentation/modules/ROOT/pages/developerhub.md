# Accelerating software development with Red Hat Developer Hub

[#redhatdevhub]
## Red Hat Developer Hub

The Red Hat® Developer Hub is a developer portal that promotes efficiency and collaboration by visually consolidating elements of the development process. It streamlines onboarding speed, developer productivity, and collaboration through a unified and open platform while reducing cognitive load and frustration for the development team. With pre-architected and supported approaches, and by centralizing technology resources, developer focus can center on delivering a competitive advantage for their organization.

Development teams can help make sure workflows run smoothly by providing the right tools, validated environments, and on-demand services. Confidence is gained that software will adhere to organizational best practices and standards without introducing unnecessary bureaucracy. Maintenance becomes more manageable, and future teams can easily assume control within a known, trusted environment, while also enhancing governance.

![](../images/devhub/dh-0.png)

Red Hat Developer Hub Benefits include:

![](../images/devhub/dh-benefit.png)

More Details about [Red Hat Developer Hub](https://developers.redhat.com/products/rhdh/overview).

## RHDH Demo Components

  ![](../images/devhub/dh-demo.png)

- Red Hat Developer Hub (RHDH)

  RHDH (i.e. Backstage) is the front end of our RHDH Demo. It gives insight into the components installed in our Clusters and how those components connect to one another.

  RHDH Demo also uses RHDH plugins to allow for the rapid deployment of new components. With the click of a button application teams can create a new code base including repo, pipelines, security, Kubernetes objects and everything required for development and deployment into OpenShift.

- Developer Workspaces

  The RHDH Demo deployment includes integration with Dev Spaces allowing development teams to have instant access to an IDE preloaded with their new code and ready for development.

- Deployment

  RHDH Demo deploys a pipeline using OpenShift Pipelines(Tekton) that run test and security scans on the code, as well as packaging the application and building/deploying the container image into a specified registry.

  OpenShift GitOps(ArgoCD) enables customers to build and integrate declarative git driven CD workflows directly into their application development platform.

- Security

  Red Hat SSO(Keycloak)
Red Hat SSO(Keycloak) is an identity management platform that secures our services (such as backstage) using a range of Identity Providers

## Access Red Hat Developer Hub

- Open Link workshop form Instructor, login with your email and password `openshift`

  ![](../images/devhub/dh-67.png)

- After Login, Workshop will show username, password and URL for this lab.

  ![](../images/devhub/dh-68.png)

- Open Red Hat Developer Hub

  ![](../images/devhub/dh-69.png)

- In Login Page, select sign in with GitLab.

  ![](../images/devhub/dh-1.png)

- Input username and password (from workshop page)

  ![](../images/devhub/dh-2.png)

- Give authorize keycloak to using OpenID Connect to your account on GitLab.

  ![](../images/devhub/dh-3.png)

- KeyCloak will redirect to the RHDH home screen.

  ![](../images/devhub/dh-4.png)

## Technical Storytelling - Golden Path

Golden Paths are a fundamental ingredient of well architected Internal Developer Platforms (IDP). Spotify uses the term “Golden Path”, but that concept has other monikers. For example Netflix calls the same idea “Paved Road.”

The general notion behind this concept is that the platform offers some form of pre-architected and supported approaches to building and deploying a particular piece of software. If a team can “stay” in the path (because the requirements of what they are building allow them to), then they get a supported road to production without having to learn all the details of the technology used to create that road.

This approach accelerates typical application development use cases (onboarding of a new team member, onboarding of a new application), and at the same time, injects the best practices that have been learned in the past. The more sophisticated Golden Paths are, the more they will be adopted, providing, as a result, more uniformity of configuration and behavior across the application portfolio.

So, what is in a Golden Path? It depends on what is being deployed. In the case of an internally developed application, a Golden Path will have at a minimum, the following ingredients:

A repository template to get started with. This could be a simple hello world application, but with the key resources and the configurations that allows any developer to get started quickly with the company IDEs and all the settings that embodies the best practices for code development.
A pipeline that can take the aforementioned repository, build it, and push the resulting artifacts all the way to production. The pipeline will have all of the steps that are deemed necessary for the organization to trust that code being deployed to production.

A set of manifests to allow for deploying the application. These could be Helm charts or kustomize configurations in the Kubernetes world, or other forms configuration descriptions, if not using Kubernetes.
Observability capability baked in. Observability (logs, traces, metrics and alerts) is a foundational capability that should be provided by the platform. Exactly what to observe in a specific application has to be defined as part of the deployment manifest. A Golden Path should provide reasonable defaults for observability settings.

Now, let’s assume that we onboard an application using a Golden Path. Here is what it might look like:

  ![](../images/devhub/dh-gold.png)

## Golden Path with Red Hat Developer Hub Software Templates Library

[Link](https://github.com/redhat-developer/red-hat-developer-hub-software-templates) Welcome to the Red Hat Developer Hub Software Templates Library. This repository contains a collection of software templates designed to illustrate best practices in software development and deployment. Our templates cover a wide range of technologies and frameworks, ensuring you have a suitable starting point for your project's needs.

- Software / Golden Templates

  Software / Golden Templates are pre-configured, best practice templates that are considered the standard for certain types of applications or environments. They are maintained with the latest recommendations and practices. Using Software Templates can help ensure that your projects adhere to industry standards and organizational policies.

  Our software templates are crafted to provide you with a solid foundation for your projects. Each template is a blueprint that includes predefined configurations, dependencies, and deployment procedures. These templates are intended to help you quickly set up and deploy applications with industry best practices.

- [Software Templates List](https://github.com/redhat-developer/red-hat-developer-hub-software-templates#how-to-use-software-templates)

  ![](../images/devhub/dh-template.png)

## Review Software Template in this lab.

- Back to workshop page, click GitLab URL (login with your username and password if it required!)

  ![](../images/devhub/dh-68.png)

- Click Project `rhdh/software-template`

  ![](../images/devhub/dh-5.png)

- Review Project Structure,

  ![](../images/devhub/dh-6.png)

- Review software-templates/scaffolders-templates, Now that we’ve seen what our template looks like, we will begin creating a software component based on this golden path template i.e. `Quarkus Service with ArgoCD and a Tekton Pipeline`. (`quarkus-web-template`)

  ![](../images/devhub/dh-7.png)

- Click on rhdh / software-templates navigate to scaffolder-templates > quarkus-web-template > template.yaml

  ![](../images/devhub/dh-8.png)

- Click on skeleton/src/main/java/${{values.java_package_name}} to view skeleton code in this template

  ![](../images/devhub/dh-9.png)

- Click on skeleton/src/main/resources to view sample properties in this template

  ![](../images/devhub/dh-10.png)

- Click on skeleton/pom.xml to view skeleton maven config in this template

  ![](../images/devhub/dh-11.png)

- Click on manifests/helm/build/templates/pipeline-build.yaml to view tekton pipeline (CI)

  ![](../images/devhub/dh-13.png)

  ![](../images/devhub/dh-14.png)

- Click on manifests/argocd/ to view argocd object (CD)

  ![](../images/devhub/dh-15.png)

  ![](../images/devhub/dh-16.png)

- Click on manifests/helm/app/templates to view kubernetes object for deployment (such as deployment, service, route, etc.)

  ![](../images/devhub/dh-17.png)

## Create Project from Software Template

- Back To Developer Hub, Once you’ve chosen the template, you will be presented with a wizard which will guide you through the creation of your software component.  As seen in the GitLab project, the fields presented to you are configured in your golden path template and will be used to create the various artifacts of your software component.

- On the RHDH home screen, click `Create…`, You will be presented with a group of tiles representing all the golden path templates created for you.

- Locate the `Quarkus Service with ArgoCD and a Tekton Pipeline` and click the CHOOSE link on the bottom right of the tile.

  ![](../images/devhub/dh-12.png)

- Once you’ve chosen the template, you will be presented with a wizard which will guide you through the creation of your software component.  As seen in the GitLab project, the fields presented to you are configured in your golden path template and will be used to create the various artifacts of your software component.
- The first part of the template requires you to complete the metadata required for the application.  You will be creating a software component called my-quarkus-app.
- Change Name to `my-quarkus-userX`, change X to your username

  ![](../images/devhub/dh-73.png)

- Leave other defaults and click Next Step

  ![](../images/devhub/dh-18.png)

- You will then be asked to provide image registry information to store your container images. We will be using our self hosted Quay instance for this demonstration.  Select `Quay` for Image Registry.
- Once you’ve selected Quay, you will be shown your quay server host, the organization your container image repositories will be stored in as well as the tag to use for your base image.
- Leave the defaults and click Next Step

  ![](../images/devhub/dh-19.png)

- You will now be presented with the target repository details where your generated software components will be installed.  Note that there will be two artifacts generated i.e. your source code repo for the app and a gitops repo to build and deploy your app.
This screen will show the git server hosting your repo’s and the GitLab group that will contain these repo’s.
- Leave the defaults and click Next Step

  ![](../images/devhub/dh-20.png)

- You will be presented with a summary of your template replacement values used to create your software component:

  ![](../images/devhub/dh-21.png)

- Click CREATE to create your software component.

  ![](../images/devhub/dh-22.png)

- Now that your software component is created, let’s take a look at the artifacts generated by this action. Based on your golden path, you would have generated a RHDH component as well as a skeleton source code repository and a gitops repository:

  - RHDH component - Your new software component is now imported into the RHDH catalog.

  - Source code repo - This is source code representing a basic quarkus application.  You may expand on this project as this is just a starting point for your development life cycle.

  - Gitops Repository - This is a repository containing artifacts such as pipelines, tasks and kubernetes resources used to set up CI/CD processes for your application.

- On the RHDH screen click `Source Code Repository` to view Source Code in GitLab.

  ![](../images/devhub/dh-23.png)

- Click on developement link

  ![](../images/devhub/dh-71.png)

- select `my-quarkus-app-gitops` project

  ![](../images/devhub/dh-72.png)

- view Gitops Repository

  ![](../images/devhub/dh-70.png)

- On the RHDH screen click `Open Component in catalog`.  If you have navigated away from this page, select Catalog on the left menu and select Component on the drop down.  Click on the my-quarkus-app component.

  ![](../images/devhub/dh-24.png)

- A new window will open displaying your new component:
  - `Links`
    - `OpenShift Dev Spaces (VS Code)` - Will open VS Code and import your app source code for editing.
    - `OpenShift Dev Spaces (JetBrains IntelliJ)` - Will open JetBrains IntelliJ and import your app source code for editing.
  - `Merge request statistics` - provides information regarding your GitLab merge requests
  - `About`
    - `View Source` - Opens up your app source code in GitLab
    - `View TechDocs` - Display any documentation you have generated for your source code (docs directory)
  - `ArgoCD overview`
    - This view shows your current status of your `ArgoCD` dev application.  It would eventually progress to a degraded state as your deployment cannot find your new quarkus app image.

  A description of the menu items at the top will be provided later on.

## Start Development with RHDH

- To get the build process running, we first need to open up our source code in Dev Spaces. In RHDH, from your my-quarkus-app component, click the OpenShift Dev Spaces (VS Code) link.

  ![](../images/devhub/dh-25.png)

- A new window will open where you will be asked to login to OpenShift.

  ![](../images/devhub/dh-26.png)

- Click Login with OpenShift and then select rhsso on the next page:

  ![](../images/devhub/dh-27.png)

- Enter your credentials provided to you for Dev Spaces on the info page of your CI and sign in.

  ![](../images/devhub/dh-28.png)

- Click Allow selected permissions on the next screen.

  ![](../images/devhub/dh-29.png)

- Click Continue for `Do you trust the authors of this repository?`

  ![](../images/devhub/dh-30.png)

- Click Authorize devspaces for grant access to your account on GitLab Community Edition.

  ![](../images/devhub/dh-31.png)

- You will then be redirected back to Dev Spaces to complete the loading of your workspace.

  ![](../images/devhub/dh-33.png)

- Wait until your workspace is ready and you see the VS Code IDE with the MY-QUARKUS-APP project loaded.  Click on the button to trust the author when prompted to do so.

  ![](../images/devhub/dh-34.png)

- Click Mark Done, for start use VS Code.

  ![](../images/devhub/dh-35.png)

- In your MY-QUARKUS-APP project, review code such as `BackendResource.java`

  ![](../images/devhub/dh-36.png)

- In your MY-QUARKUS-APP project expand the docs folder and click on the index.md file.  Add a new line at the end of the file that says `Developer Day 2025`.

  ![](../images/devhub/dh-37.png)

- Go to Source Control, Stage your commit by clicking the ‘+’ sign next to the index.md file.

  ![](../images/devhub/dh-38.png)

- Commit your changes by entering a comment and clicking the Commit button.

  ![](../images/devhub/dh-39.png)

- Finally, sync your changes to the repository by clicking the Sync Changes button.

  ![](../images/devhub/dh-40.png)

- Click OK.

  ![](../images/devhub/dh-41.png)

- Committing and syncing your changes to your source code repository automatically kicks off your build pipeline via a webhook that was configured when creating your software component. Click CI Tab, view Pipeline Runs

  ![](../images/devhub/dh-43.png)

- Click task in pipeline to view logs of process.

  ![](../images/devhub/dh-45.png)

- Wait until pipeline complete.

  ![](../images/devhub/dh-44.png)

- Click CD tab to view GitOps (ArgoCD) `my-quarkus-app-dev` Synced and Healthy.

  ![](../images/devhub/dh-47.png)

- Back to Topology tab, view `my-quarkus-app-dev` deployment

  ![](../images/devhub/dh-48.png)

- try to click `Open URL` on `my-quarkus-app-dev` deployment to check application works correctly!

  ![](../images/devhub/dh-49.png)

- Click Image Registry tab, to view Image on Quay.

  ![](../images/devhub/dh-50.png)

- Click Docs tab, to view markdown from GitLab change!

  ![](../images/devhub/dh-51.png)

## Deploy to Prepod

- Image promotion to preprod occurs when you tag your source in your source code repository. Tagging your source code in the GitLab repository triggers a webhook which starts your image promotion to preprod. In overview tab, click `view source` link.

  ![](../images/devhub/dh-52.png)

- Click on the Tags link.

  ![](../images/devhub/dh-53.png)

- Click on New tag

  ![](../images/devhub/dh-54.png)

- For the Tag name, enter `v1.0` and click Create tag.

  ![](../images/devhub/dh-55.png)

- Switch back to the pipelines page (CI tab) on your RHDH.

  ![](../images/devhub/dh-56.png)

- View Pipeline Logs.

  ![](../images/devhub/dh-57.png)

- After Complete, Back to Topology tab, test `my-quarkus-app-prepod` deployment.

  ![](../images/devhub/dh-58.png)

## Deploy to Production

- The next step is to promote your image to production.  Promotion to production occurs when you create a release in GitLab. Switch back to GitLab and select the development / my-quarkus-app repository.  Ensure you are in the root of the repository. Click the Tag link as you previously did.

  ![](../images/devhub/dh-59.png)

- On the v1.0 tag, click the Create release button.

  ![](../images/devhub/dh-60.png)

- For the Release title enter `Prod v1.0`, leave all other defaults and click the Create release button at the bottom of the screen.

  ![](../images/devhub/dh-61.png)

  ![](../images/devhub/dh-62.png)

- Your release is now created.

  ![](../images/devhub/dh-66.png)

- Switch back to CI tab on your RHDH. Wait until pipeline run complete.

  ![](../images/devhub/dh-63.png)

- Switch to Topology tab, test `my-quarkus-app-prod`

  ![](../images/devhub/dh-64.png)

- Switch to CD tab, view all ArgoCD Application Synced and Healthy.

  ![](../images/devhub/dh-65.png)

## Summary

Red Hat Developer Hub offers targeted benefits for developers, platform engineers, and organizations by simplifying workflows, improving productivity, and enabling efficient governance. Simplified access to tools, resources, and workflows through a centralized dashboard.
