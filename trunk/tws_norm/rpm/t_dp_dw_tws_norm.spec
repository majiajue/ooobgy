Name: t_dp_dw_tws_norm
Summary: 数据平台部——分词归一化器
Version: 1.0.0
Release: 1
Group: t_dp
License: commerce
BuildArchitectures: noarch

AutoReq: no

%description 
 TAG:http://svn.simba.taobao.com/svn/DW/common/tags/t_dp_dw_tws_norm_A_1_0_0_1 
 TAG:http://svn.simba.taobao.com/svn/DW/common/tags/t_dp_dw_tws_norm_A_1_0_0_1
%{_svn_path}
%{_svn_revision}

%prep

%post

%postun

%build

%install


RPM_BUILD_ROOT=./home/taobao/dw/common/tws_norm

rm -rf ${RPM_BUILD_ROOT}
mkdir -p ${RPM_BUILD_ROOT}
mkdir -p ${RPM_BUILD_ROOT}/tws
mkdir -p ${RPM_BUILD_ROOT}/normalize

cp $OLDPWD/../jni/libiconv.so.2 $RPM_BUILD_ROOT/
cp $OLDPWD/../jni/libNormalizer.so $RPM_BUILD_ROOT/
cp $OLDPWD/../jni/libTwsTokenization.so $RPM_BUILD_ROOT/
cp $OLDPWD/../zip/tws.zip $RPM_BUILD_ROOT/
cp $OLDPWD/../zip/normalize.zip $RPM_BUILD_ROOT/
cp $OLDPWD/../dist/tws-norm.jar $RPM_BUILD_ROOT/
cp $OLDPWD/../shell/norm-demo.sql $RPM_BUILD_ROOT/

unzip -d ${RPM_BUILD_ROOT}/tws ${RPM_BUILD_ROOT}/tws.zip
unzip -d ${RPM_BUILD_ROOT}/normalize ${RPM_BUILD_ROOT}/normalize.zip

%files
%defattr(0755,taobao,users)
/home/taobao/dw/common/tws_norm

%changelog
