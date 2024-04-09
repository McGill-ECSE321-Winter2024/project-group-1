<template>
    <div id="mainContainer">
        <h1>View instructors</h1>
        <VBox id="verticalContainer">
            <input id="inputBox" type="username" placeholder="Search by name"></input>

            <table id="table">
                <thead>
                    <tr>
                        <th width="20%">Profile pic</th>
                        <th width="16%">Name</th>
                        <th width="16%">Speciality</th>
                        <th width="48%">Description</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- <template v-if="instructors.length === 0"> -->
                        <!-- <tr>
                            <td colspan="4">No instructor</td>
                        </tr> -->
                    <!-- </template> -->
                    <tr>
                        <td><img id="profilePic" src="@/assets/zyzz.jpg" alt="Instructor" width="140" height="140"></td>
                        <td id="information">Zyzz Ouh</td>
                        <td>YEET</td>
                        <td>Expert in goat yoga, certified in pilates and zumba, all the moms love me, I'm the best instructor in the world (I started a cult)</td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/ronnie.jpeg" alt="Instructor" width="140" height="140"></td>
                        <td id="information">Rouni Coldmam</td>
                        <td>YEET</td>
                        <td>Expert in powerlifting, certified in squats and bench press, all the guys love him, the sexiest guy in the world (according to his girlfriend)</td>
                    </tr>

                    <tr>
                        <td><img id="profilePic" src="@/assets/john.jpg" alt="Instructor" width="140" height="140"></td>
                        <td id="information">Jon Sepa</td>
                        <td>YEET</td>
                        <td>Expert in meditation, certified in memory loss and memes, never seen at the gym tbf, I don't even know if he's still active</td>
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
            data() {
                return {
                    instructors: []
                }
            }
        }
    }
</script>

<style scoped src="../../assets/main.css"></style>