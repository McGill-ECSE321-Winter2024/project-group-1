
<template>
    <div class="Fabian" id="mainContainer">
        <h1>Propose an activity</h1>
        
        <VBox id="verticalContainer">
            <input id="inputBox" type="text" placeholder="Activity name" v-model="activityName">
            <input id="inputBox" type="text" placeholder="Description" v-model="description">
            <input id="inputBox" type="text" placeholder="Subcategory" v-model="subcategory">
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
    name: 'ProposeActivity',
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

<style scoped src="../../assets/main.css"></style>