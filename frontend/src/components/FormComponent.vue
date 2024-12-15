<script setup>
import {toRefs} from 'vue';

    const props = defineProps({
        data: Object
    });
    const {data} = toRefs(props);

    const onSubmit = () => {
        if (!!data.value.submitHandler) {
            data.value.submitHandler();
        }
    }

</script>

<template>
    <el-form  label-width="auto" style="max-width: 600px">
        <el-form-item v-for="item in data.data" :key="item.id" :label="item.label">
            <el-input v-if="item.type === 'text'" :type="item.type" v-model="item.value"></el-input>
            <el-input v-if="item.type === 'password'" :type="item.type" v-model="item.value"></el-input>
            <el-input v-if="item.type === 'number'" :type="item.type" :min="18" v-model="item.value"></el-input>
            <el-select v-if="item.type === 'select'" v-model="item.value"
                       clearable
                       :placeholder="item.placeholder">
                <el-option
                    v-for="option in item.options"
                    :key="option.id"
                    :label="option.label"
                    :value="option.value"
                ></el-option>
            </el-select>
            <el-input v-if="item.type === 'textarea'"
                      :type="item.type"
                      :placeholder="item.placeholder"
                      v-model="item.value"
                      :rows="3">
            </el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">{{data.buttonName}}</el-button>
            <el-button>Отмена</el-button>
        </el-form-item>
    </el-form>
</template>

<style scoped>

</style>