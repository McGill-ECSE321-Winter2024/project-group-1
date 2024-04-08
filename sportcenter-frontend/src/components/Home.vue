<template>
    <div id="home">
        <h1><b>Home menu</b></h1>
        <VBox id="box">
            <button id="sectionTwoButton" @click="goToLogin()">Login</button>
            <button id="sectionTwoButton" @click="goToForgotPassword()">Forgot password</button>
            <button id="sectionTwoButton" @click="goToCreateAccount()">Create a new account</button>
        </VBox>
        <VBox id="box">
            <button id="sectionTwoButton" @click="goToCustomerMode()">Customer mode</button>
            <button id="sectionTwoButton" @click="goToInstructorMode()">Instructor mode</button>
            <button id="sectionTwoButton" @click="goToOwnerMode()">Owner mode</button>
        </VBox>
        <VBox id="box">
            <button id="sectionTwoButton" @click="goToCreateActivity()">Create an activity</button>
            <button id="sectionTwoButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="sectionTwoButton" @click="goToDeleteActivity()">Delete an activity</button>
            <button id="sectionTwoButton" @click="goToScheduleActivity()">Schedule an activity</button>
            <button id="sectionTwoButton" @click="goToUpdateScheduledActivity()">Update a scheduled activity</button>
            <button id="sectionTwoButton" @click="goToDeleteScheduledActivity()">Delete a scheduled activity</button>
        </VBox>
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
            }
        }
    }
</script>

<style scoped>
    #home{
        font-family: 'Avenir', Helvetica, Arial sans-serif;
        margin: 60px;
        border-radius: 30px;
        padding: 40px;
        box-shadow: 0 50px 50px 0 rgba(209, 184, 52, 0.2);
        color: #2c3e50;
        background: #e4e3e394;
        align-items: center;
        width: 85%;
        max-width: 800px;
        max-height: fit-content;
        text-align: center;
    }

    #box {
        display: flex;
        flex-direction: column;
        justify-content: center;
        padding-top: 30px;
    }

    #sectionOneButton {
        background-color: #d1b834;
        border: none;
        color: white;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 12px;
        padding: 10px 24px;
    }

    #sectionTwoButton {
        background-color: #6d8517;
        border: none;
        color: white;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 12px;
        padding: 10px 24px;
    }
</style>