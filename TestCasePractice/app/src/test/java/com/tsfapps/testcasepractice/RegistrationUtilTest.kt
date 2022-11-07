package com.tsfapps.testcasepractice


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun empty_user_name_validate(){
        val result = RegistrationUtil.ValidateRegistrationInput(
            "",
            "Abc@123",
            "Abc@123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun user_name_already_exist(){
        val result = RegistrationUtil.ValidateRegistrationInput(
            "Tousif",
            "Abc@123",
            "Abc@123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun password_is_empty(){
        val result = RegistrationUtil.ValidateRegistrationInput(
            "Asif",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun pass_confirmPass_not_matched(){
        val result = RegistrationUtil.ValidateRegistrationInput(
            "Asif",
            "Abc@123",
            "123@Abc"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun all_case_perfect(){
        val result = RegistrationUtil.ValidateRegistrationInput(
            "Asif",
            "Abc@123",
            "Abc@123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun pass_not_contain_specialChar(){
        val result = RegistrationUtil.ValidateRegistrationInput(
            "Asif",
            "Abc123",
            "Abc123"
        )
        assertThat(result).isFalse()
    }



}