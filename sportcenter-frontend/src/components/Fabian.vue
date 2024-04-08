<template>
    <div class="fabian">
        <div class = "propose-activity">
            <h2> Propose Activity</h2>
            <div class="left-text-fields"> 
                <input type="text" placeholder="Activity name" v-model="activityName">
                <br>
                <input type="text" placeholder="Description" v-model="activityDescription">
                <br>
                <input type="text" placeholder="Subcategory" v-model="activitySubcategory">
            </div>
            <button @click="submitProposeActivity"> Propose to Owner</button>
        </div>
        <div class = "schedule-activity">
            <h2> Schedule Activity</h2>
            <div class="right-text-fields">
                <h2> Date</h2>
                <input type="date" id="scheduled" name="scheduled">
                <br>
                <h2> Start Time</h2>
                <input type="time" id="startTime" name="startTime">
                <br>
                <h2> End Time</h2>
                <input type="time" id="endTime" name="endTime">
                <br>
                <input type="text" placeholder="Account Role Id" v-model="accountRoleId">
                <br>
                <input type="text" placeholder="Activity Name" v-model="activityName">
                <br>
                <input type="text" placeholder="Capacity" v-model="capacity">
            </div>
            <button @click="submitScheduleActivity"> Schedule Activity</button>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import config from '../../config';

const client = axios.create({
    baseURL: config.dev.backendBaseUrl
});

export default {
    name: 'Fabian',
    data () {
        return {
            activityName: '',
            activityDescription: '',
            activitySubcategory: '',
            date: '',
            startTime: '',
            endTime: '',
            accountRoleId: '',
            activityName: '',
            capacity: ''
        };
    },
    methods:{
        async submitProposeActivity(){
            try{
                const response = await this.createActivity(this.activityName, this.activityDescription, this.activitySubcategory);
                console.log('Activity created', response); /*Handle success*/
            } catch(error){
                console.error('Error creating activity', error.message); /*Handle error*/
            }
        },
        async createActivity(name, description, subcategory){
            return {name,desctiption,subcategory};
        },
        async submitScheduleActivity(){
            try{
                const response = await this.cteateScheduleActivity(this.date, this.startTime, this.endTime, this.accountRoleId, this.activityName, this.capacity);
                console.log('Scheduled activity created:', response); /*Handle success*/
            } catch(error){
                console.error('Error creating scheduled activity', error.message); /*Handle error*/
            }
        },
        async createScheduleActivity(date, startTime, endTime, accountRoleId, activityName, capacity){
            return {date, startTime, endTime, accountRoleId, activityName, capacity};
        },
        clearInputs(){
            this.activityName = '';
            this.activityDescription = '';
            this.activitySubcategory = '';
            this.date = '';
            this.startTime = '';
            this.endTime = '';
            this.accountRoleId = '';
            this.activityName = '';
            this.capacity = '';
        }

    }
};

</script>
  
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.fabian {
    display: flex;
}
  
.propose-activity,
.schedule-activity {
    flex: 1;
    padding: 20px; /* Add some padding */
}
  
h2 {
    font-weight: normal;
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
    color: #42b983;
}
</style>
  