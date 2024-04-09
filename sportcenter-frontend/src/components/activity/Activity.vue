<template>
    <div id="mainContainer">
        <h1>Activity menu</h1>
        <VBox id="verticalContainer">
            <button id="mainButton" @click="goToProposeActivity()">Propose an activity</button> <!--CHANGED-->
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToDeleteActivity()">Delete an activity</button>
            <button id="mainButton" @click="goToViewActivity()">View an activity</button>
            <button id="mainButton" @click="goToScheduleActivity()">Schedule an activity</button>
            <button id="mainButton" @click="goToUpdateScheduledActivity()">Update a scheduled activity</button>
            <button id="mainButton" @click="goToDeleteScheduledActivity()">Delete a scheduled activity</button>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'Activity',
        methods: {

            goToProposeActivity() {
                this.$router.push('/app/activity/propose-activity');
            },
            goToUpdateActivity() {
                this.$router.push('/app/activity/update-activity');
            },
            goToDeleteActivity() {
                this.$router.push('/app/activity/delete-activity');
            },
            goToViewActivity() {
                this.$router.push('/app/activity/view-activity');
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

<style scoped src="../../assets/main.css">