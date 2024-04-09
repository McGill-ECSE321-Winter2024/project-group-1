<template>
<div style="width:1200px;">
    <div id="mainContainer">
        <h1>
            Home menu<br>
            <i style="color: crimson;">WARNING - Links not updated</i>
        </h1>
    </div>

    <div id="mainContainer">
        <h2>Authentification</h2>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToLogin()">Login</button>
            <button id="mainButton" @click="goToForgotPassword()">Forgot password</button>
            <button id="mainButton" @click="goToCreateAccount()">Create a new account</button>
        </VBox>
    </div>

    <div id="mainContainer">
        <h2>Account</h2>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToCustomerMode()">Customer mode</button>
            <button id="mainButton" @click="goToInstructorMode()">Instructor mode</button>
            <button id="mainButton" @click="goToOwnerMode()">Owner mode</button>
        </VBox>
    </div>

    <div id="mainContainer">
        <h2>Activity</h2>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToCreateActivity()">Create an activity</button>
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToDeleteActivity()">Delete an activity</button>
            <button id="mainButton" @click="goToScheduleActivity()">Schedule an activity</button>
            <button id="mainButton" @click="goToUpdateScheduledActivity()">Update a scheduled activity</button>
            <button id="mainButton" @click="goToDeleteScheduledActivity()">Delete a scheduled activity</button>
        </VBox>
    </div>

    <div id="mainContainer">
        <h2>Instructors</h2>
        <Vbox id="verticalContainer">
            <button id="mainButton" @click="goToInstructors()">Instructors</button>
        </Vbox>
    </div>
</div>
</template>

<script>
    export default {
        name: 'HomePage',
        methods: {
            goToLogin() {
                this.$router.push('/app/auth/login');
            },
            goToForgotPassword() {
                this.$router.push('/app/auth/forgotpassword');
            },
            goToCreateAccount() {
                this.$router.push('/app/auth/createaccount');
            },
            goToCustomerMode() {
                this.$router.push('/app/account/customer-account');
            },
            goToInstructorMode() {
                this.$router.push('/app/account/instructor-account');
            },
            goToOwnerMode() {
                this.$router.push('/app/account/owner-account');
            },
            goToCreateActivity() {
                this.$router.push('/app/activity/create-activity');
            },
            goToUpdateActivity() {
                this.$router.push('/app/activity/update-activity');
            },
            goToDeleteActivity() {
                this.$router.push('/app/activity/delete-activity');
            },
            goToScheduleActivity() {
                this.$router.push('/app/activity/schedule-activity');
            },
            goToUpdateScheduledActivity() {
                this.$router.push('/app/activity/update-scheduled-activity');
            },
            goToDeleteScheduledActivity() {
                this.$router.push('/app/activity/delete-scheduled-activity');
            },
            goToInstructors() {
                this.$router.push('/app/instructors');
            }
        }
    }
</script>

<style scoped src="../assets/main.css">