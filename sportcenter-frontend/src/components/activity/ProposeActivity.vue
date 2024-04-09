<template>
    <div class="Fabian" id="mainContainer">
        <h1>Propose an activity</h1>
        
        <VBox id="verticalContainer">
            
            <input id="inputBox" type="text" placeholder="Activity name" v-model="activityName">
            <input id="inputBox" type="text" placeholder="Description" v-model="description">
            <input id="inputBox" type="text" placeholder="Subcategory" v-model="subcategory">
            <!--<button id="mainButton" @click="created()">GetActivities</button>-->
            <button id="mainButton" @click="submitProposeActivity()">Propose to the owner</button>
        </VBox>
    </div>
</template>

<script>
import axios from "axios";
import config from "../../../config";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    //name: 'ProposeActivity',
    name: "Activities",
    data () {
        return {
            //accounts: [],
            activities: [],
            activityName: null,
            description: null,
            subcategory: null,
            date: null,
            startTime: null,
            endTime: null,
            accountRoleId: null,
            activityName2: null,
            capacity: null
        };
    },
    methods:{
        /*
        async createdAccounts(){
            try{
                const response = await AXIOS.get('/accounts');
                this.accounts = response.data.accounts; // or response.data
            } catch(error){
                console.error('Error fetching accounts', error.message);
            }
        },
        async createdActivities(){
            try{
                const response = await AXIOS.get('/activities');
                this.activities = response.data;
            } catch(error){
                console.error('Error fetching activities', error.message);
            }
        },
        */
        async submitProposeActivity(){
            const newActivity = {
                activityName: this.activityName,
                description: this.description,
                subcategory: this.subcategory
            };
            try{
                const response = await AXIOS.post('/createActivity/' + this.activityName + '/' + this.description + '/' + this.subcategory);
                this.activities.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        /*
        async submitScheduleActivity(){
            const newScheduleActivity = {
                date: this.date,
                startTime: this.startTime,
                endTime: this.endTime,
                accountRoleId: this.accountRoleId,
                activityName2: this.activityName2,
                capacity: this.capacity
            };
            try{
                const response = await AXIOS.cteateScheduleActivity("/createScheduleActivity", newScheduleActivity);
                this.createScheduleActivity.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating scheduled activity', error.message);
            }
        },
        */
        clearInputs(){
            this.activityName = null;
            this.activityDescription = null;
            this.activitySubcategory = null;
            this.date = null;
            this.startTime = null;
            this.endTime = null;
            this.accountRoleId = null;
            this.activityName2 = null;
            this.capacity = null;
        }

    }
};

</script>

<style scoped src="../../assets/main.css"></style>