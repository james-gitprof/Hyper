
const RELEASE_BRANCHES = ["master"];
const RELEASE_FILES = ["*.apk"]
const MESSAGE_FORMAT = "chore(release): ${nextRelease.version} [skip ci]\n\n${nextRelease.notes}"

const config = {
    branches: RELEASE_BRANCHES,
    plugins: ['@semantic-release/commit-analyzer',
        '@semantic-release/release-notes-generator',
        '@semantic-release/github',
        ['@semantic-release/git',
            {"assets": RELEASE_FILES,
             "message": MESSAGE_FORMAT
            }
        ]]
};

module.exports = config;
