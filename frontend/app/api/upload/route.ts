export async function POST(request: Request) {
  const proxyUrl = new URL(process.env.BACKEND_URL ?? "http://localhost:8080");
  const proxyRequest = new Request(proxyUrl, request);
  try {
    return fetch(proxyRequest);
  } catch (reason) {
    const message =
      reason instanceof Error ? reason.message : "Unexpected exception";
    return new Response(message, { status: 500 });
  }
}
