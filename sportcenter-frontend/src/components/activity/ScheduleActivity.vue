<template>
    <div class="Fabian" id="mainContainer">
        <h1>Schedule an activity</h1>

        <Vbox id="verticalContainer">
            <HBox id="horizontalContainer">
                <input id="datePickerInput" type="date" name="scheduled">
                <h2 style="align-self:center;">from</h2>
                <input id="datePickerInput" type="time" name="startTime">
                <h2 style="align-self:center;">to</h2>
                <input id="datePickerInput" type="time" name="endTime">
            </HBox>
            <select v-model="selectAccount">
                <option v-for="account in accounts" :key="account.accountRoleId" :value="account.accountRoleId">{{account.accountRoleId}}</option>>
            </select>
            <br>
            <select v-model="selectActivity">
                <option v-for="activity in activities" :key="activity.name" :value="activity.name">{{activity.name}}</option>>
            </select>
            <!--
            <input id="inputBox" type="text" placeholder="Account Role Id" v-model="accountRoleId">
            <input id="inputBox" type="text" placeholder="Activity Name" v-model="activityName2">
            -->
            <input id="inputBox" type="text" placeholder="Capacity" v-model="capacity">
            
            <button id="mainButton" @click="submitScheduleActivity()">Schedule Activity</button>
        </Vbox>
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
    //name: 'Fabian',
    name: "Activities",
    data () {
        return {
            accounts: [],
            selectAccount: null,
            activities: [],
            selectActivity: null,

            activityName: null,
            description: null,
            subcategory: null,
            date: null,
            startTime: null,
            endTime: null,
            //accountRoleId: null,
            //activityName2: null,
            capacity: null
        };
    },
    mounted(){
        this.createdAccounts();
        this.createdActivities();
    },
    methods:{
        /*
        formatDate(event){
            const selectedDate = new Date(event.target.value);
            const localDate = DateTime.fromJSDate(selectedDate).toLocal(); // Convert to local time zone
            const formattedDate = localDate.toISODate(); 
        },
        */
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
        async submitProposeActivity(){
            const newActivity = {
                name: this.activityName,
                description: this.description,
                subcategory: this.subcategory
            };
            try{
                const response = await AXIOS.post('/createActivity', newActivity);
                this.createActivity.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating activity', error.message);
            }
        },
        async submitScheduleActivity(){
            const newScheduleActivity = {
                date: this.date,
                startTime: this.startTime,
                endTime: this.endTime,
                selectAccount: this.selectAccount,
                selectActivity: this.selectActivity,
                capacity: this.capacity
            };
            try{
                /*
                const localDate = DateTime.fromJSDate(date).toLocal(); // Convert to local date
                date = localDate.toISODate(); 

                const localStartTime = DateTime.fromJSTime(startTime).toLocal(); // Convert to local time zone
                startTime = localStartTime.toISOTime();

                const localEndTime = DateTime.fromJSTime(endTime).toLocal(); // Convert to local time zone
                endTime = localEndTime.toISOTime(); 
                */

                const response = await AXIOS.createScheduleActivity("/createScheduleActivity", newScheduleActivity);
                this.createScheduleActivity.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating scheduled activity', error.message);
            }
        },
        clearInputs(){
            this.selectAccount = null;
            this.selectActivity = null;
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