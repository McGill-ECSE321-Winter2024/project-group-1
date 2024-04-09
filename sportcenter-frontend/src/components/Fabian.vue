<template>
    <div class="something">
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
                    <button id="submitPropose" @click="created()"><b>Get Activities</b></button>
                    <button id="submitPropose" @click="submitProposeActivity()"><b>Propose to Owner</b></button>
                    <button class="danger-btn" @click="deleteActivities()">Clear</button>
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
                            <h2> Start Time</h2>
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
        <h2>Activities</h2>
        <div class="activities">
        <table>
            <tbody id="activities-tbody">
                <tr>
                    <th>Activity Name</th>
                    <th>Description</th>
                    <th>Subcategory</th>
                </tr>
                <tr v-for="a in activities">
                    <td>{{ a.name }}</td>
                    <td>{{ a.description }}</td>
                    <td>{{ a.subcategory }}</td>
                </tr>
            </tbody>
        </table>
        </div>
    </div>
</template>
  
<script>
import axios from "axios";
import config from "../../config";

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const client = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: "Activities",
    data() {
        return {
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
        async created(){
            try{
                const response = await client.get('/activities');
                this.activities = response.data;
            } catch(error){
                console.log('Error fetching activities', error.message);
            }
        },
        async submitProposeActivity(){
            const newActivity = {
                activityName: this.activityName,
                description: this.description,
                subcategory: this.subcategory
            };
            try{
                const response = await client.post('/createActivity/' + this.activityName + '/' + this.description + '/' + this.subcategory);
                this.activities.push(response.data);
                this.clearInputs();
            } catch(error){
                console.log('Error creating activity', error.message);
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
                const response = await client.createScheduleActivity("/createScheduleActivity", newScheduleActivity);
                this.createScheduleActivity.push(response.data);
                this.clearInputs();
            } catch(error){
                console.error('Error creating scheduled activity', error.message);
            }
        },
        clearInputs(){
            this.activityName = null;
            this.description = null;
            this.subcategory = null;
            this.date = null;
            this.startTime = null;
            this.endTime = null;
            this.accountRoleId = null;
            this.activityName2 = null;
            this.capacity = null;
        },
        async deleteActivities(){
            for(let activity of this.activities) {
                try{
                    await client.delete('/activity/delete/' + activity.name);
                } catch(error){
                    console.log('Error deleting activities', error.message);
                }
            }
            this.activities = [];
        }
    },
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
.activities {
    display: flex;
    flex-direction: column;
    padding: 20px;
    font-weight: normal;
    font-size: 18px;
    align-items: center-stretch;
}

td,
th {
    padding: 0.5em;
    border: 1px solid black;
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
  