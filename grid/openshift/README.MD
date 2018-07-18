# How to use Selenium Grid on OpenShift

The .yaml files on this directory can be found at [https://github.com/ddavison/selenium-openshift-templates](https://github.com/ddavison/selenium-openshift-templates) and additional information can be found at [http://www.assertselenium.com/continuous-delivery/setting-up-selenium-grid-on-kubernetes/](http://www.assertselenium.com/continuous-delivery/setting-up-selenium-grid-on-kubernetes/)

## Create the grid enviroment on OpenShift

Follow the steps:

1. Login into your Openshift account using `oc` command line
2. Navigato to this directory
3. Create the Selenium Hub template running `oc create -f selenium-hub.yaml`
4. Create the Selenium Google Chrome template running `oc create -f selenium-node-chrome.yaml`
4. Create the Selenium Firefox template running `oc create -f selenium-node-firefox.yaml`

