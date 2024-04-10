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
                        <th>Activity Name</th>
                        <th>Activity Description</th>
                        <th>Activity Approve</th>
                    </tr>
                </thead>
                <tbody id="activities-tbody">
                    <template v-if="activities.length === 0">
                        <tr>
                            <td colspan="3">No activities</td>
                        </tr>
                    </template>
                    <tr v-for="activity in activities">
                        <td>{{ activity.name }}</td>
                        <td>{{ activity.description }}</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton" @click="approveActivity()">Approve</button>
                                <button id="subButton" @click="dissaproveActivity()">Disapprove</button>
                            </VBox>
                        </td>
                    </tr>
                </tbody>
                <!--
                <thead>
                    <tr>
                        <th width="12%">Profile pic</th>
                        <th width="16%">Name</th>
                        <th width="16%">Speciality</th>
                        <th width="46%">Description</th>
                        <th width="10%">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <template v-if="instructors.length === 0">
                        <tr>
                            <td colspan="5">No instructor</td>
                        </tr>
                    </template>
                    <tr>
                        <td><img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">Zyzz Ouh</td>
                        <td>Bodybuilding</td>
                        <td>Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Other</button>
                            </VBox>
                        </td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/ronnie.jpeg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">Rouni Coldmam</td>
                        <td>Strength</td>
                        <td>Expert in powerlifting, certified in squats and bench press, all the guys love him, the sexiest guy in the world (according to his girlfriend)</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Other</button>
                            </VBox>
                        </td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/john.jpg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">Jon Sepa</td>
                        <td>Cardio</td>
                        <td>Expert in meditation, certified in memory loss and memes, never seen at the gym tbf, I don't even know if he's still active</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Other</button>
                            </VBox>
                        </td>
                    </tr>
                </tbody> -->
            </table>
        </VBox>
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
            activities: []
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
        
        async approveActivity(){
            try{
                const response = await AXIOS.put('/approveActivity')
                this.activities = response.data
            } catch (error){
                console.log('Error fetching activities', error.message);
            }
        },
        async dissaproveActivity(){
            try{
                const response = await AXIOS.put('/dissaproveActivity')
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