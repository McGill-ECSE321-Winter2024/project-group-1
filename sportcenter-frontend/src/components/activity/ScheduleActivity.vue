<template>
    <div class="Fabian" id="mainContainer">
        <h1>Schedule an activity</h1>

        <Vbox id="verticalContainer">
            <!-- <HBox id="horizontalContainer">
                <VBox id="verticalContainer">
                    <h2>Date</h2>
                    <input id="datePickerInput" type="date" name="scheduled">
                </VBox>

                <VBox id="verticalContainer" style="margin-left: 20px;">
                    <H2>Start time</h2>
                    <input id="datePickerInput" type="time" name="startTime">
                </VBox>

                <VBox id="verticalContainer" style="margin-left: 20px;">
                    <h2>End time</h2>
                    <input id="datePickerInput" type="time" name="endTime">
                </VBox>
            </HBox> -->

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
  
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.fabian {
    display: flex;
    padding: 20px;
    flex-direction: row;
}
.textDate {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.textStart{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.textEnd{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
  
.propose-activity,
.schedule-activity {
    flex: 1;
    padding: 20px; /* Add some padding */
}

h1 {
    font-weight: normal;
    font-size: 24px;
}
  
h2 {
    font-weight: normal;
    font-size: 18px;
}

#proposeBox {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    background-color: grey;
    border-radius: 30px;
}

#scheduleBox {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    background-color: grey;
    border-radius: 30px;
}

#submitPropose {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 30px;
    width: 100%;
}

#scheduleActivity {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 30px;
    width: 100%;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    display: inline-block;
    margin: 0 10px;
}

a {
    color: white;
}
</style>
  
<style scoped src="../../assets/main.css"></style>