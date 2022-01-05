<template>
  <v-app>
    <v-main>
      <BugManagement/>
    </v-main>
  </v-app>
</template>

<script>
import BugManagement from './components/BugManagement';

export default {
  name: 'App',

  components: {
    BugManagement,
  },

  data: () => ({
    //
  }),
};
</script>
