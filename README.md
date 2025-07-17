# FruitCropXL Output Module

Author: Ou-An Chuang (<ou-an.chuang@plantandfood.co.nz>)

Based on example by James Bristow.

## Table of contents

- [FruitCropXL Output Module](#fruitcropxl-output-module)
  - [Table of contents](#table-of-contents)
  - [Usage](#usage)

## Usage

### Deploying to Maven Central Repository

Ensure you have write access to the `io.github.fruitcropxl` Maven Central Repository namespace by configuring your `settings.xml` (located in `/home/<username/.m2/` on Linux) (see [Publishing By Using the Maven Plugin](https://central.sonatype.org/publish/publish-portal-maven/)).

You may be prompted for a GPG key to sign the files. Please see how to [configure and distribute a public GPG key](https://central.sonatype.org/publish/requirements/gpg/). Remember to send your GPG key to `keyserver.ubuntu.com` to distribute it before use.

Run the following:

```sh
mvn clean deploy
```

Navigate to [Publishing Settings](https://central.sonatype.com/publishing/deployments) on the Maven Central Repository web interface. The deployment should be visible as either Pending or Validating.

Publish the deployment once its status has been changed to Validated.
