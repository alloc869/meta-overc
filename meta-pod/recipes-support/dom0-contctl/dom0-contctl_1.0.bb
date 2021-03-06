SUMMARY = "Domain 0 Container Control Tool"
DESCRIPTION = "A tool for deploying, querying, etc lxc containers from Domain 0"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

RDEPENDS_${PN} = "util-linux lxc"

SRC_URI = "file://dom0_contctl \
           file://lxc_driver.sh \
           file://dom0-containers \
           file://dom0-contctl.service \
           file://lxc_common_helpers.sh \
           file://lxc_driver_net.sh \
           file://lxc_hook_net_pre-mount.sh \
           file://dpdk_nic_bind_wrapper.py \
           file://lxc_hook_net_mount.sh \
           file://dom0-contctl.conf \
           file://lxc_launch_group.sh \
"

SRC_FILES_LIST="dom0_contctl \
lxc_driver.sh \
dom0-containers \
lxc_common_helpers.sh \
lxc_driver_net.sh \
lxc_hook_net_pre-mount.sh \
dpdk_nic_bind_wrapper.py \
lxc_hook_net_mount.sh \
lxc_launch_group.sh \
"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "dom0-contctl.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"

do_install() {
    dom0_contctl_dir=${D}/opt/dom0-contctl

    install -d ${D}/opt/
    install -d ${dom0_contctl_dir}
    for i in ${SRC_FILES_LIST}; do
        install -m 0755 ${WORKDIR}/${i} ${dom0_contctl_dir}
    done

    install -d ${D}/lib/systemd/system/
    install -m 0644 ${WORKDIR}/dom0-contctl.service ${D}/lib/systemd/system/

    install -d ${D}/${sysconfdir}
    install -m 0744 ${WORKDIR}/dom0-contctl.conf ${D}/${sysconfdir}
}

FILES_${PN} += "/opt \
                /lib/systemd/system \
                ${sysconfdir} \
"
