<template>
    <div id="mainContainer">
        <h1>Activity menu</h1>
        <VBox id="CustomerContainer" v-if="$accountType=='Guest'">
            <button id="mainButton" @click="goToViewActivity()">View an activity</button>
        </VBox>
        <VBox id="CustomerContainer" v-if="$accountType=='Customer'">
            <button id="mainButton" @click="goToViewActivity()">View an activity</button>
        </VBox>
        <VBox id="CustomerContainer" v-if="$accountType=='Instructor'">
            <button id="mainButton" @click="goToProposeActivity()">Propose an activity</button> <!--CHANGED-->
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToScheduleActivity()">Schedule an activity</button>
            <button id="mainButton" @click="goToUpdateDeleteScheduledActivity()">Update a scheduled activity</button>
        </VBox>
        <VBox id="verticalContainer" v-if="$accountType=='Owner'">
            
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToUpdateDeleteScheduledActivity()">Update a scheduled activity</button>
            <button id="mainButton" @click="goToManageActivities()">Manage activities</button>
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
                this.$router.push('/app/activity/update-delete-activity');
            },
            goToDeleteActivity() {
                this.$router.push('/app/activity/update-delete-activity');
            },
            goToViewActivity() {
                this.$router.push('/app/activity/view-activity');
            },
            goToScheduleActivity() {
                this.$router.push('/app/activity/schedule-activity');
            },
            goToUpdateDeleteScheduledActivity() {
                this.$router.push('/app/activity/update-delete-scheduled-activity');
            },
            goToManageActivities() {
                this.$router.push('/app/activity/manage-activities');
            },
        }
    }
</script>

<style scoped src="../../assets/main.css">