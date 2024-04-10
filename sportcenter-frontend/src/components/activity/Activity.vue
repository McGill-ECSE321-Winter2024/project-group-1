<template>


    <div id="mainContainer">
        <h1>Activity Menu</h1>
        <VBox id="CustomerContainer" v-if="$accountType=='Guest'">
            <button id="mainButton" @click="goToViewActivity()">View an activity</button>
        </VBox>
        <VBox id="CustomerContainer" v-else-if="$accountType === 'Customer'">
            <button id="mainButton" @click="goToViewActivity()">View an activity</button>
        </VBox>
        <VBox id="CustomerContainer" v-else-if="$accountType=='Instructor'">
            <button id="mainButton" @click="goToProposeActivity()">Propose an activity</button> <!--CHANGED-->
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToScheduleActivity()">Schedule an activity</button>
            <button id="mainButton" @click="goToUpdateDeleteScheduledActivity()">Update a scheduled activity</button>
        </VBox>
        <VBox id="verticalContainer" v-else="$accountType ==='Owner'">
            
            <button id="mainButton" @click="goToUpdateActivity()">Update an activity</button>
            <button id="mainButton" @click="goToUpdateDeleteScheduledActivity()">Update a scheduled activity</button>
            <button id="mainButton" @click="goToManageActivities()">Manage activities</button>
        </VBox>
    


    <VBox id="verticalContainer">
            <!--
            <input id="inputBox" type="username" placeholder="Search by name"></input>
            <input id="inputBox" type="username" placeholder="Search by activity"></input>
            -->

            <table id="table">
                <thead>
                    <tr>
                        <th width="100">Activity Name</th>
                        <th width="100">Activity Description</th>

                        <th v-if="$accountType ==='Owner'" width="100">Activity Approve</th>
                    </tr>
                </thead>
                <tbody id="activities-tbody">
                    <template v-if="activities.length === 0">
                        <tr>
                            <td colspan="3">No activities</td>
                        </tr>
                    </template>
                    <template v-else>
                    <tr v-for="(activity, index) in activities" :key="index">
                        <td>{{ activity.name }}</td>
                        <td>{{ activity.description }}</td>
                        <td v-if="$accountType === 'Owner'">
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
                this.activities = response.data
            } catch (error){
                console.log('Error fetching activities', error.message);
            }
            },
            async dissaproveActivity(activity){
                try{
                    const response = await AXIOS.put('/activity/disapprove/' + activity)
                    this.activities = response.data
                } catch (error){
                    console.log('Error fetching activities', error.message);
                }
            }


        }
    }
</script>

<style scoped src="../../assets/main.css">