// @elysian-dawnbreaker
// Config for setting up releases and whatnot.
// Remember to update or check over the .yaml file before commit

// for testing before applying to other branches
// remember to omit this and change it with something else once ready for actual use

const config = {
    //branches: [ADDENDUM_DISTRIBUTION_BRANCH,{name: TESTING_DISTRIBUTION_BRANCH, prerelease: true}]
    branches: [{name: "integrator-testing", prerelease: true}, "integrator-testing-2"],
    plugins: [
    "@semantic-release/commit-analyzer",
    "@semantic-release/release-notes-generator",
    "@semantic-release/github",
    ["@semantic-release/git", {
    "assets": ["*.apk"], // get anything that has .apk file, should be in $GITHUB_WORKSPACE of the runner
    }]
    ]
}

module.exports = config;