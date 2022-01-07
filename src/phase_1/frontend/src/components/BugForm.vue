<template>
  <v-form
    ref="form"
    v-model="syncValid"
  >
    <v-text-field
      outlined
      label="Summary"
      v-model="syncSummary"
      :rules="[requiredRule]"
      required
    ></v-text-field>
    <v-textarea
      outlined
      label="Description"
      v-model="syncDescription"
      :rules="[requiredRule]"
      required
    ></v-textarea>
  </v-form>
</template>

<script>
export default {
  name: 'BugFrom',
  props: {
    summary: String,
    description: String,
    valid: Boolean
  },
  computed:{
    syncSummary:{
      get(){
        return this.summary
      },
      set(val){
        this.$emit('update:summary',val)
      }
    },
    syncDescription:{
      get(){
        return this.description
      },
      set(val){
        this.$emit('update:description',val)
      }
    },
    syncValid:{
      get(){
        return this.valid
      },
      set(val){
        this.$emit('update:valid',val)
      }
    }
  },
  data() {
    return {
      requiredRule: v => !!v || 'required',
    }
  },
  methods: {
    validate () {
      this.$refs.form.validate()
    },
    resetValidation () {
      this.$refs.form.resetValidation()
    },
  }
}
</script>
