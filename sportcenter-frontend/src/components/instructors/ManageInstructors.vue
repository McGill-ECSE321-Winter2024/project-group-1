<template>
    <div id="mainContainer">
        <h1>Manage instructors</h1>
        <VBox id="verticalContainer">
            <input id="inputBox" type="username" placeholder="Search by name"></input>
            <input id="inputBox" type="username" placeholder="Search by activity"></input>

            <table id="table">
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
                    <!-- <template v-if="instructors.length === 0"> -->
                        <!-- <tr>
                            <td colspan="5">No instructor</td>
                        </tr> -->
                    <!-- </template> -->
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

                    <tr>
                        <td><img id="profilePic" src="@/assets/messi.jpg" alt="Instructor" width="120" height="120"></td>
                        <td id="information">The Little Guy from Rizzario</td>
                        <td>GOAT</td>
                        <td>Expert in football, World Cup winner, MVP, fuck Ronaldo</td>
                        <td>
                            <VBox id="verticalContainer">
                                <button id="subButton">Approve</button>
                                <button id="subButton">Disapprove</button>
                                <button id="subButton">Other</button>
                            </VBox>
                        </td>
                    </tr>
                </tbody>
            </table>
        </VBox>
    </div>
</template>

<script>
    export default {
        name: 'Login',
        methods: {
            /* TODO */
        }
    }
</script>

<style scoped src="../../assets/main.css"></style>