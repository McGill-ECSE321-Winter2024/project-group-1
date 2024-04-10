<template>
    <div id="mainContainer">
        <h1>Manage activities</h1>
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
                        <th width="100">Activity Approve</th>
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
                                <button id="approveSubButton" @click="approveActivity(activity.name)">Approve</button>
                                <button id="disapproveSubButton" @click="dissaproveActivity(activity.name)">Disapprove</button>
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

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'Login',
    data() {
        return {
            activities: [],
            instructors: []
        }
    },
    async created(){
        try{
                const response = await AXIOS.get('/activities')
                this.activities = response.data
            } catch (error){
                console.log('Error fetching activities', error.message);
            }
        
        try{
                const instructorResponse = await AXIOS.get('/getAllInstructors')
                this.instructorResponse = response.data
            } catch (error){
                console.log('Error fetching activities', error.message);
            }
        },
    methods: {
        
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
};
</script>

<style scoped>
  #profilePic {
    border-radius: 15px;
  }
</style>

<style scoped src="../../assets/main.css"></style>