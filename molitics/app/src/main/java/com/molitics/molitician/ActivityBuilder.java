package com.molitics.molitician;

import com.molitics.molitician.base.ActivityFragmentModule;
import com.molitics.molitician.ui.dashboard.ActivityFragment;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.changePassword.provider.ChangePasswordFragmentModule;
import com.molitics.molitician.ui.dashboard.channels.ChannelBankActivity;
import com.molitics.molitician.ui.dashboard.channels.ChannelListActivity;
import com.molitics.molitician.ui.dashboard.channels.module.ChannelBankModule;
import com.molitics.molitician.ui.dashboard.channels.module.ChannelModule;
import com.molitics.molitician.ui.dashboard.dashboard.dashBoardModule.DashBoardActivityModule;
import com.molitics.molitician.ui.dashboard.home.provider.HomeFragmentProvider;
import com.molitics.molitician.ui.dashboard.hotTopics.HotTopicActivity;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.language.LanguageActivity;
import com.molitics.molitician.ui.dashboard.language.module.LanguageModule;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.liveVideo.provider.LiveVideoProvider;
import com.molitics.molitician.ui.dashboard.more.InviteToContactActivity;
import com.molitics.molitician.ui.dashboard.more.NearbyUserFragment;
import com.molitics.molitician.ui.dashboard.more.module.InviteToContactModule;
import com.molitics.molitician.ui.dashboard.more.provider.MoreTabFragmentProvider;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.newsDetail.module.NewsDetailsActivtyModule;
import com.molitics.molitician.ui.dashboard.newsDetail.viewModel.NewsDetailProvider;
import com.molitics.molitician.ui.dashboard.register.forgotPassword.provider.ForgotFragmentModule;
import com.molitics.molitician.ui.dashboard.register.forgotPassword.provider.ForgotPasswordResetModule;
import com.molitics.molitician.ui.dashboard.register.location.provider.DistrictLocationFragmentModule;
import com.molitics.molitician.ui.dashboard.register.location.provider.LocationFragmentModule;
import com.molitics.molitician.ui.dashboard.register.otp.provider.OtpFragmentModule;
import com.molitics.molitician.ui.dashboard.register.password.provider.PasswordFragmentModule;
import com.molitics.molitician.ui.dashboard.register.provider.SignInFragmentModule;
import com.molitics.molitician.ui.dashboard.register.provider.SignUpFragmentModule;
import com.molitics.molitician.ui.dashboard.register.register.provider.RegisterFragmentModule;
import com.molitics.molitician.ui.dashboard.splash.SplashActivity;
import com.molitics.molitician.ui.dashboard.splash.SplashActivityProvider;
import com.molitics.molitician.ui.dashboard.userProfile.module.NearByUserModule;
import com.molitics.molitician.ui.dashboard.userProfile.module.UserProfileModule;
import com.molitics.molitician.ui.dashboard.verification.VerificationActivity;
import com.molitics.molitician.ui.dashboard.verification.dagger.VerificationActivityModule;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.ui.dashboard.voice.module.CreateVoiceActivityModule;
import com.molitics.molitician.ui.dashboard.voice.provider.CreateVoiceFragmentModule;
import com.molitics.molitician.ui.dashboard.voice.provider.VoiceFragmentProvider;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailActivity;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.module.VoiceDetailActivityModule;
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.YoutubeMoreActivity;
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.module.YoutubeMoreModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {CreateVoiceActivityModule.class, CreateVoiceFragmentModule.class})
    abstract CreateVoiceActivity bindVoiceActivity();

    @ContributesAndroidInjector(modules = VoiceDetailActivityModule.class)
    abstract VoiceDetailActivity bindVoiceDetailActivity();

    @ContributesAndroidInjector(modules = {NewsDetailsActivtyModule.class, NewsDetailProvider.class})
    abstract DetailNewsActivity bindDetailNewsActivity();

    @ContributesAndroidInjector(modules = {DashBoardActivityModule.class, VoiceFragmentProvider.class,
            MoreTabFragmentProvider.class, HomeFragmentProvider.class, LiveVideoProvider.class})
    abstract DashBoardActivity bindDashBoardActivity();

    @ContributesAndroidInjector(modules = {VoiceFragmentProvider.class})
    abstract NewLeaderProfileActivity bindLeaderProfileActivity();

    @ContributesAndroidInjector()
    abstract HotTopicActivity bindHotTopicActivity();

    //@ContributesAndroidInjector(modules = UserProfileFragmentModule.class)
    @ContributesAndroidInjector(modules = {CreateVoiceActivityModule.class, CreateVoiceFragmentModule.class})
    abstract HotTopicDetailActivity bindHotTopicDetailActivity();

    @ContributesAndroidInjector(modules = UserProfileModule.class)
    abstract VoiceCreatorProfile bindVoiceCreateDetailActivity();

    @ContributesAndroidInjector(modules = NearByUserModule.class)
    abstract NearbyUserFragment bindNearByUserActivity();

    @ContributesAndroidInjector(modules = InviteToContactModule.class)
    abstract InviteToContactActivity bindInviteToContactActivity();

    @ContributesAndroidInjector(modules = YoutubeMoreModule.class)
    abstract YoutubeMoreActivity bindYoutubeMoreActivity();

    @ContributesAndroidInjector(modules = {ActivityFragmentModule.class, SignInFragmentModule.class,
            SignUpFragmentModule.class, OtpFragmentModule.class, PasswordFragmentModule.class,
            ForgotFragmentModule.class, RegisterFragmentModule.class, LocationFragmentModule.class,
            DistrictLocationFragmentModule.class, ChangePasswordFragmentModule.class,
            ForgotPasswordResetModule.class})
    abstract ActivityFragment bindActivityFragment();

    @ContributesAndroidInjector(modules = LanguageModule.class)
    abstract LanguageActivity bindLanguageActivity();

    @ContributesAndroidInjector(modules = ChannelModule.class)
    abstract ChannelListActivity bindChannelActivity();

    @ContributesAndroidInjector(modules = ChannelBankModule.class)
    abstract ChannelBankActivity bindChannelBankActivity();

    @ContributesAndroidInjector(modules = SplashActivityProvider.class)
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector(modules = {VerificationActivityModule.class})
    abstract VerificationActivity bindVerificationActivity();
}
