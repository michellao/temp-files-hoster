import { withSentryConfig } from "@sentry/nextjs";
import type { NextConfig } from "next";

let nextConfig: NextConfig = {
  output: "standalone",
  devIndicators: false,
  experimental: {
    typedEnv: true,
  },
};

if (
  process.env.SENTRY_ORG &&
  process.env.SENTRY_PROJECT &&
  process.env.NEXT_PUBLIC_SENTRY_DSN
) {
  nextConfig = withSentryConfig(nextConfig, {
    org: process.env.SENTRY_ORG,
    project: process.env.SENTRY_PROJECT,

    silent: !process.env.CI,

    disableLogger: true,
  });
}

export default nextConfig;
