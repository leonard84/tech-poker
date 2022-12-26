[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/leonard84/tech-poker/blob/master/LICENSE.txt)

# Tech Poker

[Try it online](https://techpoker.onrender.com/)

As it is hosted on a free Render host, it may take a while to boot up on first load.

## What is this?

Tech Poker is a webapp to collaboratively use the [planning poker](https://en.wikipedia.org/wiki/Planning_poker) methodology.

It was developed during a hackathon, has proven its usefulness, and is now actively used by multiple teams.

## How to Deploy?

Just create a Account at [Render](https://render.com) and a Docker (Web Service) project there.

After linking this repo to the project, you just need to do two steps to make the app run:<br>
- Set the 'Docker Command' in the settings to: `java -jar /app/techpoker.jar`.
- Set the environment variable `SPRING_PROFILES_ACTIVE=render`.

That should be all to build successful on Render.