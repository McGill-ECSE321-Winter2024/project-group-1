<template>
    <div class="Fabian">
        <h1><b>Instructor Page</b></h1>
        <div class="fabian">
            <div id="proposeBox" class = "propose-activity">
                <VBox id="containerF">
                    <h1><b> Propose Activity</b></h1>
                    <div class="left-text-fields"> 
                        <input type="text" placeholder="Activity name" v-model="activityName">
                        <br>
                        <input type="text" placeholder="Description" v-model="description">
                        <br>
                        <input type="text" placeholder="Subcategory" v-model="subcategory">
                    </div>
                    <button id="submitPropose" @click="submitProposeActivity()"><b>Propose to Owner</b></button>
                </VBox>
            </div>
            <div id="scheduleBox" class = "schedule-activity">
                <VBox id="containerF2">
                <h1><b>Schedule Activity</b></h1>
                    <div class="right-text-fields">
                        <div class="textDate">
                            <h2> Date</h2>
                            <input type="date" id="scheduled" name="scheduled">
                        </div>
                        <div class="textStart">
                            <div class="textStart"></div>
                            <input type="time" id="startTime" name="startTime">
                        </div>
                        <div class="textEnd">
                            <h2> End Time</h2>
                            <input type="time" id="endTime" name="endTime">
                        </div>
                        <input type="text" placeholder="Account Role Id" v-model="accountRoleId">
                        <br>
                        <input type="text" placeholder="Activity Name" v-model="activityName2">
                        <br>
                        <input type="text" placeholder="Capacity" v-model="capacity">
                    </div>
                    <button id="scheduleActivity" @click="submitScheduleActivity()"><b>Schedule Activity</b></button>
                </VBox>
            </div>
        </div>
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
  