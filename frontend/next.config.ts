import { withSentryConfig } from "@sentry/nextjs";
import type { NextConfig } from "next";

const requiredEnvVars = ["BACKEND_URL"];

requiredEnvVars.forEach((envVar) => {
  if (!process.env[envVar]) {
    throw new Error(`Missing required environment variable: ${envVar}`);
  }
});

let nextConfig: NextConfig = {
  devIndicators: false,
  experimental: {
    typedEnv: true,
  },
  async rewrites() {
    return {
      afterFiles: [
        {
          source: "/:path*",
          destination: `${process.env.BACKEND_URL}/:path*`,
        },
      ],
    };
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
