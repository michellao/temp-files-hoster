import type { NextConfig } from "next";

const requiredEnvVars = ['NEXT_PUBLIC_BACKEND_URL'];

requiredEnvVars.forEach((envVar) => {
  if (!process.env[envVar]) {
    throw new Error(`Missing required environment variable: ${envVar}`);
  }
});

const nextConfig: NextConfig = {
  devIndicators: false,
  output: 'export'
};

export default nextConfig;
