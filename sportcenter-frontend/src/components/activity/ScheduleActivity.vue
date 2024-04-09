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

            <input id="inputBox" type="text" placeholder="Account Role Id" v-model="accountRoleId">
            <input id="inputBox" type="text" placeholder="Activity Name" v-model="activityName2">
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
    name: 'Fabian',
    data () {
        return {
            activityName: '',
            description: '',
            subcategory: '',
            date: '',
            startTime: '',
            endTime: '',
            accountRoleId: '',
            activityName2: '',
            capacity: ''
        };
    },
    methods:{
        async submitProposeActivity(){
            const newActivity = {
                name: this.activityName,
                description: this.activityDescription,
                subcategory: this.activitySubcategory
            };
            try{
                const response = await AXIOS.post('http://localhost:8080/createActivity', newActivity);
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
                accountRoleId: this.accountRoleId,
                activityName2: this.activityName2,
                capacity: this.capacity
            };
            try{
                const response = await AXIOS.cteateScheduleActivity("http://localhost:8080/createScheduleActivity", newScheduleActivity);
                this.createScheduleActivity.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating scheduled activity', error.message);
            }
        },
        clearInputs(){
            this.activityName = '';
            this.activityDescription = '';
            this.activitySubcategory = '';
            this.date = '';
            this.startTime = '';
            this.endTime = '';
            this.accountRoleId = '';
            this.activityName2 = '';
            this.capacity = '';
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