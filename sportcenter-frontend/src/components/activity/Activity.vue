<template>


    <div id="mainContainer">
        <h1>Activity Menu</h1>
        <VBox id="CustomerContainer" v-if="getAccountType()=='Guest'">
            <button id="mainButton" @click="goToViewActivity()">View scheduled activities</button>
        </VBox>
        <VBox id="CustomerContainer" v-else-if="getAccountType() === 'Customer'">
            <button id="mainButton" @click="goToViewActivity()">View scheduled activities</button>
        </VBox>
        <VBox id="CustomerContainer" v-else-if="getAccountType() === 'Instructor'">
            <button id="mainButton" @click="goToProposeActivity()">Propose an activity</button> <!--CHANGED-->
            <button id="mainButton" @click="goToScheduleActivity()">Schedule an activity</button>
        </VBox>
        <VBox id="verticalContainer" v-else-if="getAccountType() ==='Owner'">
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToUpdateDeleteScheduledActivity()">Update a scheduled activity</button>
            <button id="mainButton" @click="goToManageActivities()">Manage activities</button>
        </VBox>
    <br>
    <br>

    <h1>Our selection</h1>
    <VBox id="verticalContainer">
            <table id="table">
                <thead>
                    <tr>
                        <th width="100">Activity Name</th>
                        <th width="100">Activity Description</th>
                        <th v-if="getAccountType() === 'Instructor' || getAccountType() === 'Owner'" width="100">Activity Status</th>

                        <th v-if="getAccountType() ==='Owner'" width="100">Activity Approve</th>
                    </tr>
                </thead>
                <tbody id="activities-tbody">
                    <template v-if="activities.length === 0">
                        <tr>
                            <td colspan="4">No activities</td>
                        </tr>
                    </template>
                    <template v-else>
                    <tr v-for="(activity, index) in activities" :key="index">
                        <td>{{ activity.name }}</td>
                        <td>{{ activity.description }}</td>
                        <td v-if="getAccountType() === 'Instructor' | getAccountType() === 'Owner'"> {{ activity.isApproved ? "Approved" : "Not Approved" }} </td>
                        <td v-if="getAccountType() === 'Owner'">
                            <VBox id="verticalContainer">
                                <button id="subButton" @click="approveActivity(activity.name)">Approve</button>
                                <button id="subButton" @click="dissaproveActivity(activity.name)">Disapprove</button>
                            </VBox>
                        </td>
                    </tr>
                    </template>
                </tbody>
            </table>
        </Vbox>
    </div>
</template>

<script>
import axios from 'axios';
import config from '../../../config';
import ManageActivities from './ManageActivities.vue';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


    export default {
        name: 'Activity',
        data() {
            return {
                activities: [],
            }
            
        },

        async created(){
        try{
                const response = await AXIOS.get('/activities')
                this.activities = response.data
            } catch (error){
                console.log('Error fetching activities', error.message);
            }
        },

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

            async approveActivity(activity){
                try{
                    const response = await AXIOS.put('/activity/approve/' + activity)
                    this.$forceUpdate();
                } catch (error){
                    console.log('Error fetching activities', error.message);
                }
            },
            async dissaproveActivity(activity){
                try{
                    const response = await AXIOS.put('/activity/disapprove/' + activity)
                    this.$forceUpdate();
                } catch (error){
                    console.log('Error fetching activities', error.message);
                }
            },
            getAccountType() {
                return localStorage.getItem('accountType');
            },
        }
    }
</script>

<style scoped src="../../assets/main.css">